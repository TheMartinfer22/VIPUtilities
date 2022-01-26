package dev.nanosync.viputilities.api;

import dev.nanosync.viputilities.VIPUtilities;
import lombok.SneakyThrows;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;

import java.util.ArrayList;
import java.util.List;

public class DiscordAPI {

    private JDA jda;
    private String guildID;
    private List<Object> registeredListeners = new ArrayList<>();


    @SneakyThrows
    public void build(String token, String guildID){
        jda = JDABuilder.createDefault(token).build();
        this.guildID = guildID;
    }

    /**
     * Pode ser utilizado para enviar mensagens entre outros recursos.
     *
     * @return : Retorna todos métodos presentes da Guild
     */
    public Guild getGuild(){
        return jda.getGuildById(guildID);
    }

    /**
     * Utilizada para criar funções personalizadas caso não contenha na API Nano
     *
     * @return JDA, interface principal do DiscordJDA
     */
    public JDA getJda(){
        return jda;
    }

    /**
     * Método para registrar classes de eventos do Discord
     * Após seu uso deve ser utilizar o registersBuild().
     *
     * @param clazz : Classe que está presente a extensão de ListenerAdapter
     * @return this
     */
    public DiscordAPI registerListener(Object clazz){
        registeredListeners.add(clazz);
        return this;
    }

    /**
     * Constroi todos registros de eventos.
     */
    public void registersBuild(){
        jda.addEventListener(registeredListeners);
    }

}
