package com.trab3;

public class PathNormalize {
    /**
     * Converte um caminho path do formato "path/to/file" para "path\to\file" se o sistema operativo em uso for o Windows
     *
     * @param path o caminho a converter (se necessário)
     *
     * @return O caminho convertido para utilizadores Windows ou o valor passado para utilizadores de UNIX
     */
    public static String parse(String path) {
        return System.getProperty("os.name").startsWith("Windows") ? path.replace("/", "\\") : path;
    }
}