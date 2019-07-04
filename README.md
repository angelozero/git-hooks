# Git Hooks
[How to Use Git Hooks in Your Development Workflow](https://hackernoon.com/how-to-use-git-hooks-in-your-development-workflow-a94e66a0f3eb)

[Prepend Git Commit Messages](http://blog.bartoszmajsak.com/blog/2012/11/07/lazy-developers-toolbox-number-1-prepend-git-commit-messages/)


```javascript

#!/bin/bash

# This way you can customize which branches should be skipped when
# prepending commit message. 
if [ -z "$BRANCHES_TO_SKIP" ]; then
  BRANCHES_TO_SKIP=(master develop test)
fi

BRANCH_NAME=$(git symbolic-ref --short HEAD)
BRANCH_NAME="${BRANCH_NAME##*/}"

BRANCH_EXCLUDED=$(printf "%s\n" "${BRANCHES_TO_SKIP[@]}" | grep -c "^$BRANCH_NAME$")
BRANCH_IN_COMMIT=$(grep -c "\[$BRANCH_NAME\]" $1)

if [ -n "$BRANCH_NAME" ] && ! [[ $BRANCH_EXCLUDED -eq 1 ]] && ! [[ $BRANCH_IN_COMMIT -ge 1 ]]; then 
  sed -i.bak -e "1s/^/[$BRANCH_NAME] /" $1
fi
```
