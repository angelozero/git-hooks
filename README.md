# Git Hooks

**Fonts:**

[How to Use Git Hooks in Your Development Workflow](https://hackernoon.com/how-to-use-git-hooks-in-your-development-workflow-a94e66a0f3eb)

[Prepend Git Commit Messages](https://medium.com/@nicklee1/prepending-your-git-commit-messages-with-user-story-ids-3bfea00eab5a)

[Validating if all tests are passing with GitHook](https://www.youtube.com/watch?v=MF72e-12dxE)

[Run mvn before commit](https://codepad.co/snippet/running-junit-test-before-push-on-git-with-maven)

---

- *Just rename ```prepare-commit-msg.sample``` to ```prepare-commit-msg```, and dont forgeit to chmod +x in the file.*

---

#### Executando o comando mvn clean install / test antes do commit
- pre-commit
```javascript
#!/bin/bash

echo "Executando testes ..."
# git-commit ---> nome do projeto
mvn -f git-commit clean install &> ~/tests_log.txt
if [[ $? != 0 ]] ; then
	echo "Testes falhando, verifique no arquivo ~/tests_log.txt"
	exit 1
fi
echo "Tudo OK"
# ok
exit 0

```

#### Adicionando o nome da branch antes do comentário
- prepare-commit-msg
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
#### Validando se a branch a ser criada segue o nome padrão pré determinado
- ???
```javascript
```

