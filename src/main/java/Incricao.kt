import com.google.cloud.pubsub.v1.MessageReceiver
import com.google.cloud.pubsub.v1.Subscriber
import com.google.pubsub.v1.ProjectSubscriptionName
import com.google.pubsub.v1.PubsubMessage
import java.util.concurrent.LinkedBlockingDeque

object Incricao {
    fun inscrever(projectid: String, assinantesId: String) {
        //val projectId = projectid//supergrupo
        val mensagens = LinkedBlockingDeque<PubsubMessage>()
        //val assinantesID = assinantesId
        val nomeDosAssinantes = ProjectSubscriptionName.of(projectid, assinantesId)
        var assinante: Subscriber? = null

        try {
            assinante = Subscriber.newBuilder(nomeDosAssinantes, MessageReceiver { message, consumer ->
                mensagens.offer(message)
                consumer.ack()
            }).build()
            assinante.startAsync().awaitRunning()

            while (true) {
                val message = mensagens.take()
                println("Message ID: ${message.messageId}\nData: ${message.data.toStringUtf8()}")
            }
        } catch (e: Exception) {
            println(e.message)
        } finally {
            assinante?.stopAsync()
        }
    }
}
