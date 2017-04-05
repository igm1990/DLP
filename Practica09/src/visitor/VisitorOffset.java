package visitor;

import visitor.util.VisitorAbstract;

// Mirar el ambito

// Atributo en el visitor sumatoria variable globales
// el ambito debe ser 0

// El ambito es 1: variable local
// Sumatorio de variables locales
// Resetearlo al entrar

// Parametro
// Tiene el mismo ambito que variable local
// No propagamos la llamada(accept), calculamos in situ
// Recorremos la lista de parametros desde el ultimo al primero sin accept y miramos el ambito
// No salir de ahi

// Campos
// En el propio tiporegisto calculamos (como en parametros)
// no hacemos accept
public class VisitorOffset extends VisitorAbstract {

}