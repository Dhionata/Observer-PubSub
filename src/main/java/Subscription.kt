import com.google.cloud.pubsub.v1.MessageReceiver
import com.google.cloud.pubsub.v1.Subscriber
import com.google.pubsub.v1.ProjectSubscriptionName
import com.google.pubsub.v1.PubsubMessage
import java.util.concurrent.LinkedBlockingDeque

object Subscription {
    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val projectId = "fasam-1984"
        val mensagens = LinkedBlockingDeque<PubsubMessage>()
        val subscriptionId = "inscritos"
        val projectSubscriptionName = ProjectSubscriptionName.of(projectId, subscriptionId)
        var subscriber: Subscriber? = null

        try {
            subscriber = Subscriber.newBuilder(projectSubscriptionName, MessageReceiver { message, consumer ->
                mensagens.offer(message)
                consumer.ack()
            }).build()
            subscriber.startAsync().awaitRunning()

            while (true) {
                val message = mensagens.take()
                println("Message ID: ${message.messageId}\nData: ${message.data.toStringUtf8()}")
            }
        } catch (e: Exception) {
            println(e.message)
        } finally {
            subscriber?.stopAsync()
        }
    }
}
