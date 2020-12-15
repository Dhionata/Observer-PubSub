import kotlin.Throws
import kotlin.jvm.JvmStatic
import com.google.pubsub.v1.ProjectSubscriptionName
import com.google.cloud.pubsub.v1.MessageReceiver
import com.google.pubsub.v1.PubsubMessage
import com.google.cloud.pubsub.v1.AckReplyConsumer
import java.util.concurrent.TimeUnit
import java.io.IOException
import java.util.concurrent.ExecutionException
import java.lang.InterruptedException
import com.google.pubsub.v1.TopicName
import com.google.protobuf.ByteString
import com.google.api.core.ApiFuture
import com.google.cloud.pubsub.v1.Publisher
import com.google.cloud.pubsub.v1.TopicAdminClient
import java.lang.Exception

object PublisherExample {
    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {
        // TODO(developer): Replace these variables before running the sample.
        val projectId = "fasam-1984"
        val topicId = "test3"
        publisherExample(projectId, topicId)
    }

    @Throws(IOException::class, ExecutionException::class, InterruptedException::class)
    fun publisherExample(projectId: String?, topicId: String?) {
        val topicName = TopicName.of(projectId, topicId)
        var publisher: Publisher? = null
        try {
            //cria o tópico
            val topicAdminClient = TopicAdminClient.create()
            topicAdminClient.createTopic(topicName)
            // Create a publisher instance with default settings bound to the topic
            publisher = Publisher.newBuilder(topicName).build()
            val message = "Hello World! 8"
            val data = ByteString.copyFromUtf8(message)
            val pubsubMessage = PubsubMessage.newBuilder().setData(data).build()

            // Once published, returns a server-assigned message id (unique within the topic)
            val messageIdFuture = publisher.publish(pubsubMessage)
            val messageId = messageIdFuture.get()
            println("Published message ID: $messageId")
            println("Published message : $message")
        } finally {
            if (publisher != null) {
                // When finished with the publisher, shutdown to free up resources.
                publisher.shutdown()
                publisher.awaitTermination(1, TimeUnit.MINUTES)
            }
        }
    }
}