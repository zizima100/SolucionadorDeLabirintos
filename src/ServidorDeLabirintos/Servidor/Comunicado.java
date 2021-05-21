package ServidorDeLabirintos.Servidor;

import java.io.*;
import java.io.Serializable;

/**
 * Classe da qual todas as outras classes herdam. Ela deve estar presente tanto
 * no cliente quanto no servidor.
 * <p>
 * Objetos dessa classe devem ser ocos, ou seja, não devem conter nenhum
 * atributo ou método.
 * </p>
 */
public class Comunicado implements Serializable {
}

// Apenas objetos que implementem a interface Serializable podem ser
// transmitidos através de sockets, salvos em arquivos ou inseridos num banco de
// dados.