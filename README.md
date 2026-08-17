# FocusZone — starter da Aula 8

1. Atualmente o FocusZone não tem um botão de pausar, apenas iniciar e cancelar, zerando a contagem. Para resolver isso, decidi que após clicar o botão de start focus, invés de ele ficar disabled, esse mesmo botão será o botão de pausar a contagem.

TimerStateHolder
- Foi criado a variavel nomeTarefa, com um getter e o metodo atualizarNomeTarefa(). Isso vai ser usado para que o nome da tarefa permaneça no aplicativo caso esteja pausado e reaberto o aplicativo após fechar, salvando o progresso e nome.

TimerService
- O metodo iniciarContagem() sempre calculava o tempo baseado no tempo total do focus, foi ajustado para que caso tenha uma tarefa iniciada, ela calcula baseada no tempo restante invés.

TimerViewModel
- No TimerViewModel foi implementado o metodo pausarContagem() para que envia um intent para TimerService.ACTION_PAUSAR
Em TimerService, criamos uma variavel Long chamada de tempoRestanteAtual,

