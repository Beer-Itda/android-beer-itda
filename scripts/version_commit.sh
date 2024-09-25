#!/bin/bash

git config --global user.name "github-actions"
git config --global user.email "github-actions@hjiee.github.com"
echo "${{ inputs.release_version }}"
if [ "${{ inputs.release_environment }}" = "prod" ]; then
  # production 환경일 경우 alpha 태그를 제거합니다
  version=$(echo "${{ inputs.release_version }}" | sed 's/-alpha[0-9]*//')
else
  # qa 또는 dev 환경일 경우 원래 버전을 그대로 사용합니다
  version="${{ inputs.release_version }}"
fi

sed -i "s/const val VERSION_NAME = ".*"/const val VERSION_NAME = \"$version\"/" buildSrc/src/main/kotlin/AndroidConfig.kt
grep "VERSION_NAME" buildSrc/src/main/kotlin/AndroidConfig.kt

# 변경사항이 있는지 확인합니다
if ! git diff --quiet || ! git diff --staged --quiet; then
  # 변경사항이 있으면 커밋하고 푸시합니다
  git add .
  git commit -m "change version name $version"

  git fetch origin
  # 현재 브랜치 이름을 가져옵니다
  current_branch=$(git rev-parse --abbrev-ref HEAD)

  # rebase를 시도합니다
  if git rebase origin/$current_branch; then
    echo "Rebase successful"
  else
    echo "Rebase failed. Attempting merge..."
    git rebase --abort
    git merge origin/$current_branch
  fi

  # 푸시를 시도합니다
  if git push origin $current_branch; then
    echo "Push successful"
  else
    echo "Push failed. Please check the repository permissions and try again."
    exit 1
  fi


  # production 환경인 경우에만 태그를 생성합니다
  if [ "${{ inputs.release_environment }}" = "prod" ]; then
   git tag -a "v$version" -m "Release $version"
   git push origin "v$version"
  fi
fi