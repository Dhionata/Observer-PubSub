import com.google.api.gax.rpc.ApiException
import com.google.cloud.pubsub.v1.TopicAdminClient
import com.google.pubsub.v1.TopicName

object CreateTopic {
    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val projectId = "fasam-1984"
        val topicId = "meu-topico"

        val topic = TopicName.of(projectId, topicId)
        try {
            val topicAdminClient = TopicAdminClient.create()
            topicAdminClient.createTopic(topic)
        } catch (e: ApiException) {
            println(e.statusCode.code)
            println(e.isRetryable)
            println(e.message)
        }
        println("Topic ${topic.project}")
    }
}