# Git Hooks

**Fonts:**

[How to Use Git Hooks in Your Development Workflow](https://hackernoon.com/how-to-use-git-hooks-in-your-development-workflow-a94e66a0f3eb)

[Prepend Git Commit Messages](https://medium.com/@nicklee1/prepending-your-git-commit-messages-with-user-story-ids-3bfea00eab5a)

[Validating if all tests are passing with GitHook](https://www.youtube.com/watch?v=MF72e-12dxE)

[Run mvn before commit](https://codepad.co/snippet/running-junit-test-before-push-on-git-with-maven)

---

- *Just rename ```prepare-commit-msg.sample``` to ```prepare-commit-msg```, and dont forgeit to chmod +x in the file.*

---

#### Validando o nome da Branch definindo e e xecutando o comando mvn clean install / test antes do push
- pre-push
```javascript
#!/bin/bash

LC_ALL=C

local_branch="$(git rev-parse --abbrev-ref HEAD)"

valid_branch_regex="^(ITAU)\-[a-z0-9._-]+$"

message="\nO Nome da Branch esta fora da Nomenclatura Correta. Os nomes das Branch neste projeto devem seguir o seguinte padrão:\n $valid_branch_regex.
\nVocê deve renomear sua Branch para um nome válido. E tentar novamente"


if [[ ! $local_branch =~  $valid_branch_regex ]]; then
        echo "Nome da Branch fora do padrão"
	exit 1

else 
    	echo "Executando testes ..."
	# git-commit ---> nome do projeto
	mvn -f git-commit clean install &> ~/tests_log.txt
	
	if [[ $? == 0 ]] ; then
		echo "TUDO OK"		
	else
		echo "Testes falhando, verifique no arquivo ~/tests_log.txt"
		exit 1
		
	fi	

fi

exit 0

```

#### Adicionando o nome da branch antes do comentário
- prepare-commit-msg
```javascript
#!/bin/bash
#
#
#
# Branchs que devem ser ignoradas
#
	if [ -z "$BRANCHES_TO_SKIP" ]; then
  		BRANCHES_TO_SKIP=(master develop staging test)
	fi
#
#
# Get the current branch name and check if it is excluded
#
#
BRANCH_NAME=$(git symbolic-ref --short HEAD)
BRANCH_EXCLUDED=$(printf "%s\n" "${BRANCHES_TO_SKIP[@]}" | grep -c "^$BRANCH_NAME$")

#
#
# Trim
#
#
TRIMMED=$(echo $BRANCH_NAME | sed -e 's:^\([^-]*-[^-]*\)-.*:\1:' -e \
    'y/abcdefghijklmnopqrstuvwxyz/ABCDEFGHIJKLMNOPQRSTUVWXYZ/')
#
#
# 
#
#
if [ -n "$BRANCH_NAME" ] &&  ! [[ $BRANCH_EXCLUDED -eq 1 ]]; then
  sed -i.bak -e "1s/^/[$TRIMMED] /" $1
fi
```
