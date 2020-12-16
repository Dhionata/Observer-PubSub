import com.google.cloud.pubsub.v1.SubscriptionAdminClient
import com.google.pubsub.v1.ProjectSubscriptionName
import com.google.pubsub.v1.PushConfig
import com.google.pubsub.v1.TopicName

object SubscribeAsyncExample {
    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val projectId = "fasam-1984"
        val topicId = "meu-topico"
        val subscriptionId = "inscritos"
        subscribeAsyncExample(projectId, subscriptionId, topicId)
    }

    private fun subscribeAsyncExample(projectId: String?, subscriptionId: String?, topicId: String?) {
        val topicName = TopicName.of(topicId, subscriptionId)
        val subscriptionName = ProjectSubscriptionName.of(projectId, subscriptionId)
        try {
            val subscriptionAdminClient = SubscriptionAdminClient.create()
            subscriptionAdminClient.createSubscription(subscriptionName, topicName, PushConfig.getDefaultInstance(), 0)
        } catch (e: Exception) {
            println(e.message)
        } finally {
            println("Subscription ${subscriptionName.project}:${subscriptionName.subscription} created.\n")
        }
    }
}