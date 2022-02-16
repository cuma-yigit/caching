package caching.multiinstance;

import com.rabbitmq.client.Channel;
import common.AMQPCommon;

public class RemoveItem {

	public static void main(String[] args) throws Exception {
		if (args.length == 0) {
			System.out.println("specify a product to remove from your wishlist");
			return;
		}

		String product = "REMOVE," + args[0];
		Channel channel = AMQPCommon.connect();
		byte[] message = product.getBytes();
		String routingKey = "wishlist.request.q";
		channel.basicPublish("", routingKey, null, message);
		AMQPCommon.close(channel);
	}
}
