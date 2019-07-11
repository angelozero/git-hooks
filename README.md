# Git Hooks

**Hook para validar o nome da branch pré-definida e rodar os testes via ```mvn clean install``` antes do push**

**Hook para adicionar o nome da branch antes do comentário no comando ```git commit -m ""```**

---

 - Acesse dentro do diretório do projeto a pasta oculta ```~/.git/hooks```

 - Nela você encontrará uma série de arquivos ```.sample```, estes arquivos são usados para criarem os hooks.

 - Aqui vamos usar 2 arquivos, o ```pre-push.sample``` e o ``` prepare-commit-msg```.

 - Para ambos os arquivos faça as seguintes configurações:

	- Remova a extensão ```.sample``` dos arquivos usados ( EX: Renomeie o arquivo ```prepare-commit-msg.sample``` para ```prepare-commit-msg``` )
 
	- E via terminal use o comando ```chmod +x ``` para alterar suas permissões. ( EX.: ```chmod +x prepare-commit-msg``` )

---

**Hook usado no comando ```git push``` para validar o nome da branch pré-defininda e executando o comando mvn clean install**
- *pre-push (aqui o nome da branch padrão esta como "ANGELO-", aceitando após o traço qualquer valor)*
```shell
#!/bin/bash

LC_ALL=C

local_branch="$(git rev-parse --abbrev-ref HEAD)"

valid_branch_regex="^(ANGELO)\-[a-z0-9._-]+$"

project_folder_name="git-hooks"

message="O nome da branch esta fora do padrão pré-definido. Renomeie a branch usando o comando git branch -m nome_branch_atual_errado nome_branch_valido para corrigir."

skip_branchs=("master develop bufgix")


if [[  ! $local_branch =~  $valid_branch_regex ]] && [[ ! " ${skip_branchs[@]} " =~ " $local_branch " ]]  ; then
	echo $message
	exit 1

else 
    	echo "Executando testes ..."
	
	mvn -f $project_folder_name clean install &> ~/tests_log.txt
	
	if [[ $? == 0 ]] ; then
		echo "TESTES OK"		
	else
		echo "Testes falhando, verifique no arquivo ~/tests_log.txt"
		exit 1
		
	fi	

fi

exit 0
```
---

**Hook usado no comando ```git commit``` para validar adicionar o nome da branch antes da mensagem do commit**
- *prepare-commit-msg ( deve adicionar antes do commit o valor da branch e ignorando se caso a branch for master, develop staging ou test. EX.: "[ANGELO-...] - mensagem_do_commit"*
```shell
#!/bin/bash

# Branchs que devem ser ignoradas
#
if [ -z "$BRANCHES_TO_SKIP" ]; then
	BRANCHES_TO_SKIP=(master develop staging test)
fi

# Get the current branch name and check if it is excluded
#
#
BRANCH_NAME=$(git symbolic-ref --short HEAD)
BRANCH_EXCLUDED=$(printf "%s\n" "${BRANCHES_TO_SKIP[@]}" | grep -c "^$BRANCH_NAME$")

# Trim
#
#
TRIMMED=$(echo $BRANCH_NAME | sed -e 's:^\([^-]*-[^-]*\)-.*:\1:' -e \
    'y/abcdefghijklmnopqrstuvwxyz/ABCDEFGHIJKLMNOPQRSTUVWXYZ/')

if [ -n "$BRANCH_NAME" ] &&  ! [[ $BRANCH_EXCLUDED -eq 1 ]]; then
  sed -i.bak -e "1s/^/[$TRIMMED] /" $1
fi
```

---

**Fonts:**

[How to Use Git Hooks in Your Development Workflow](https://hackernoon.com/how-to-use-git-hooks-in-your-development-workflow-a94e66a0f3eb)

[Prepend Git Commit Messages](https://medium.com/@nicklee1/prepending-your-git-commit-messages-with-user-story-ids-3bfea00eab5a)

[Validating if all tests are passing with GitHook](https://www.youtube.com/watch?v=MF72e-12dxE)

[Run mvn before commit](https://codepad.co/snippet/running-junit-test-before-push-on-git-with-maven)
