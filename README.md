# FocusZone — starter da Aula 8

Projeto no estado ensinado em sala: o timer roda dentro de um `TimerService`
(Foreground Service), com notificação persistente e um botão "Pausar". O foco continua
contando mesmo com o app em background ou a tela bloqueada.

Falta um jeito de manter o `TimerService` vivo quando o app é removido dos Recents (não só
minimizado pelo Home) — veja os `// TODO` em `TimerService.java` e `AndroidManifest.xml`, e
o enunciado completo em `../enunciado.md` antes de começar.
