import com.google.cloud.pubsub.v1.SubscriptionAdminClient
import com.google.pubsub.v1.ProjectSubscriptionName
import com.google.pubsub.v1.PushConfig
import com.google.pubsub.v1.TopicName

object Assinantes {

    fun Assinar(projectId: String?, topicId: String?, assinantesID: String) {
        //val projetoID = projectId//supergrupo
        // val topicoID = topicId//tópico
        //val assinantesID = assinantesID//grupoDeInscrições
        subscribeAsyncExample(projectId, assinantesID, topicId)
    }

    private fun subscribeAsyncExample(projectId: String?, subscriptionId: String?, topicId: String?) {
        val nomeDoTopico = TopicName.of(projectId, topicId)
        val nomeDosAssinantes = ProjectSubscriptionName.of(projectId, subscriptionId)
        try {
            val clienteAdministradorDeAssinantes = SubscriptionAdminClient.create()
            clienteAdministradorDeAssinantes.createSubscription(nomeDosAssinantes,
                nomeDoTopico,
                PushConfig.getDefaultInstance(),
                0)
        } catch (e: Exception) {
            println(e.message)
        } finally {
            println("Incrição ${nomeDosAssinantes.project}:${nomeDosAssinantes.subscription} criado.\n")
        }
    }
}