package com.erickmob.telegram.slavebot.service;


import com.erickmob.telegram.slavebot.domain.Command;
import com.erickmob.telegram.slavebot.domain.Ranking;
import com.erickmob.telegram.slavebot.domain.TelegramUser;
import com.erickmob.telegram.slavebot.domain.UserLogs;
import com.erickmob.telegram.slavebot.repository.TelegramUserRepository;
import com.erickmob.telegram.slavebot.repository.UserLogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@Component
public class MessageService {

    private static final Charset UTF_8 = Charset.forName("UTF-8");
    private static  byte[] emojiTrophyBytes = new byte[]{(byte)0xF0, (byte)0x9F, (byte)0x8F, (byte)0x86};

    @Autowired
    private UserLogsRepository userActionRepository;

    @Autowired
    private TelegramUserRepository telegramUserRepository;

    public String getHelpMessage() {
        return "Opa, aqui vai uma pequena ajuda!.  \n"
            + "Por enquanto só entendo os seguintes comandos: \n"
            + "/uno : Adiciono uma nova vitória de UNO nesse grupo e te falo quantas vezes você já ganhou nesse jogo nesse grupo.\n"
            + "/detetive : Adiciono uma nova vitória de DETETIVE nesse grupo e te falo quantas vezes você já ganhou nesse jogo nesse grupo.\n"
            + "/ludo : Adiciono uma nova vitória de LUDO nesse grupo e te falo quantas vezes você já ganhou nesse jogo nesse grupo.\n"
            + "/vida : Adiciono uma nova vitória de JOGO DA VIDA nesse grupo e te falo quantas vezes você já ganhou nesse jogo nesse grupo.\n"
            + "/Placar: Mostro o placar do grupo atualizado.";
    }

    public String getPlacarMessage(Message message) {
        String placarMessage = new String("Segue placar ").concat(new String(emojiTrophyBytes, Charset.forName("UTF-8"))).concat(":\n");
        List<UserLogs> userlog = userActionRepository.findAllByChatID(message.getChatId());

        List<Ranking> top3 = new ArrayList<>();
        List<Ranking> ranking = new ArrayList<>();
        generateTop3(userlog, top3, ranking);
        for (Map.Entry<Command, List<Ranking>> entry :
                top3.stream().collect(groupingBy(Ranking::getCommand, toList())).entrySet()) {
            placarMessage += createScoreMessageByCommand(entry);
        }
        return placarMessage;
    }

    private String createScoreMessageByCommand(Map.Entry<Command, List<Ranking>> entry) {
        String scoreStringByCommand = "\nJogo: ".concat(entry.getKey().getActionDone()).concat("\n");
        TelegramUser telegramUser;
        for(Ranking ranking : entry.getValue()){
            telegramUser = telegramUserRepository.findByUserID(ranking.getUserId());
            scoreStringByCommand +=  "Vitórias: "+ranking.getTotalWins() +" - "+ telegramUser.getUsername()+ "\n";
        }
        return scoreStringByCommand;
    }

    private void generateTop3(List<UserLogs> userlog, List<Ranking> top3, List<Ranking> ranking) {
        Map<Command, List<UserLogs>> map = userlog.stream().collect(groupingBy(UserLogs::getCommand));
        map.entrySet().forEach( e -> agruparPorUsuario(ranking, e.getKey(), e.getValue()));

        //sorting
        ranking.sort(Comparator.comparing(Ranking::getCommand)
                .thenComparing(Ranking::getTotalWins).reversed());

        ranking.stream().collect(groupingBy(Ranking::getCommand, toList()))
                .entrySet().stream()
                .forEach( i -> {
                    if(i.getValue().size() >3){
                        i.getValue()
                                .subList(0,3)
                                .forEach( x -> top3.add(x));
                    }else{
                        i.getValue()
                                .forEach(x -> top3.add(x));
                    }
                });
    }

    private static void agruparPorUsuario(List<Ranking> ranking, Command key, List<UserLogs> value) {
        Map<Integer, List<UserLogs>> commandGroupedByUser = value.stream().collect(groupingBy(UserLogs::getUserID));
        commandGroupedByUser.entrySet().forEach( e -> ranking.add(new Ranking(key, e.getKey(), e.getValue().stream().count())));
    }
}
