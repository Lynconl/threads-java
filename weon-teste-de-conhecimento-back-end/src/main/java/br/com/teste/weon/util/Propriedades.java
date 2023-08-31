package br.com.teste.weon.util;

import br.com.teste.weon.Main;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Propriedades {

    private static Propriedades instance;

    public static synchronized Propriedades getInstance() {
        if (instance == null) {
            instance = new Propriedades();
        }

        return instance;
    }

    Properties properties;

    public Propriedades() {
        try {
            properties = new Properties();
            properties.load(getPropertiesFile());
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar arquivo de properties: ", e);
        }
    }

    public InputStream getPropertiesFile() {
        return Main.class.getClassLoader().getResourceAsStream("sistema.properties");
    }

    public String get(String propriedade) {
        if (properties.containsKey(propriedade)) {
            return properties.getProperty(propriedade);
        } else {
            throw new IllegalArgumentException(String.format("Propriedade: % não encontrada no properties", propriedade));
        }
    }
}
