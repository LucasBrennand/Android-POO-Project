package br.ufpe.cin.focuszone.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class TimerActionReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (TimerService.ACTION_BROADCAST_PAUSAR.equals(intent.getAction())) {
            Intent comando = new Intent(context, TimerService.class);
            comando.setAction(TimerService.ACTION_PAUSAR);
            context.startService(comando);
        }
    }
}
