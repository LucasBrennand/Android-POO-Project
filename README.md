# FocusZone — starter da Aula 8

1. Atualmente o FocusZone não tem um botão de pausar, apenas iniciar e cancelar, zerando a contagem. Para resolver isso, decidi que após clicar o botão de start focus, invés de ele ficar disabled, esse mesmo botão será o botão de pausar a contagem.

No TimerViewModel foi implementado o metodo pausarContagem() para que envia um intent para TimerService.ACTION_PAUSAR


