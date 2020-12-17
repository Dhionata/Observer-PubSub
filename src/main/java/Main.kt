import javax.swing.JOptionPane

fun main(args: Array<String>) {

    /*val nomeDoProjeto = JOptionPane.showInputDialog(null,
        "Nome do projeto",
        "ProjectName",
        JOptionPane.QUESTION_MESSAGE)*/

    val nomeDoProjeto = "fasam-1984"

    val nomeDoTopico = JOptionPane.showInputDialog(null, "Nome do tópico", "TopicName", JOptionPane.QUESTION_MESSAGE)
    CriadorDeTopico.criar(nomeDoProjeto, nomeDoTopico)

    val nomeAssinantes =
        JOptionPane.showInputDialog(null, "Nome dos Assinantes", "SubscritionName", JOptionPane.QUESTION_MESSAGE)

    Assinantes.Assinar(nomeDoProjeto, nomeDoTopico, nomeAssinantes)

    /*object : Thread() {
        override fun run() {
            Incricao.main(args)
        }
    }.start()*/

    do {
        val mensagens =
            JOptionPane.showInputDialog(null, "Digite sua mensagem", "Message", JOptionPane.QUESTION_MESSAGE)
        Publicador.publicar(nomeDoProjeto, nomeDoTopico, mensagens)
    } while (0 == JOptionPane.YES_NO_OPTION)
}