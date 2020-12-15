import com.google.cloud.pubsub.v1.AckReplyConsumer
import com.google.cloud.pubsub.v1.MessageReceiver
import com.google.cloud.pubsub.v1.Subscriber
import com.google.pubsub.v1.ProjectSubscriptionName
import com.google.pubsub.v1.PubsubMessage
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

object SubscribeAsyncExample {
    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val projectId = "fasam-1984"
        val subscriptionId = "test3"
        subscribeAsyncExample(projectId, subscriptionId)
    }

    private fun subscribeAsyncExample(projectId: String?, subscriptionId: String?) {
        val subscriptionName = ProjectSubscriptionName.of(projectId, subscriptionId)

        // Instantiate an asynchronous message receiver.
        val receiver = MessageReceiver { message: PubsubMessage, consumer: AckReplyConsumer ->
            // Handle incoming message, then ack the received message.
            println("Id: " + message.messageId)
            println("Data: " + message.data.toStringUtf8())
            consumer.ack()
        }
        var subscriber: Subscriber? = null
        try {
            subscriber = Subscriber.newBuilder(subscriptionName, receiver).build()
            // Start the subscriber.
            subscriber.startAsync().awaitRunning()
            System.out.printf("Listening for messages on %s:\n", subscriptionName.toString())
            // Allow the subscriber to run for 30s unless an unrecoverable error occurs.
            subscriber.awaitTerminated(50, TimeUnit.SECONDS)
        } catch (e: Exception) {
            // Shut down the subscriber after 30s. Stop receiving messages.
            subscriber!!.stopAsync()
            println(e.message)
        }
    }
}