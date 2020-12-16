import com.google.cloud.pubsub.v1.Publisher
import com.google.protobuf.ByteString
import com.google.pubsub.v1.PubsubMessage
import com.google.pubsub.v1.TopicName
import java.io.IOException
import java.util.concurrent.ExecutionException
import java.util.concurrent.TimeUnit

object PublisherExample {
    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val projectId = "fasam-1984"//supergrupo
        val topicId = "teste"//tópico
        publisherExample(projectId, topicId)
    }

    @Throws(IOException::class, ExecutionException::class, InterruptedException::class)
    private fun publisherExample(projectId: String?, topicId: String?) {
        val topicName = TopicName.of(projectId, topicId)
        var publisher: Publisher? = null
        try {
            publisher = Publisher.newBuilder(topicName).build()
            repeat(10) {
                val message = "Mensagem número $it"

                val data = ByteString.copyFromUtf8(message)
                val pubsubMessage = PubsubMessage.newBuilder().setData(data).build()

                val messageIdFuture = publisher.publish(pubsubMessage)
                println("Published message ID: ${messageIdFuture.get()}")
                println("Published message : $message\n-----\n\n")
            }
        } catch (e: Exception) {
            println("Erro ${e.message}")
        } finally {
            if (publisher != null) {
                publisher.shutdown()
                publisher.awaitTermination(1, TimeUnit.MINUTES)
            }
        }
    }
}