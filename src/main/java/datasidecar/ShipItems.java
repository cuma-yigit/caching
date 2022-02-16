package caching.datasidecar;

import com.rabbitmq.client.Channel;
import common.AMQPCommon;

public class ShipItems {

	public static void main(String[] args) throws Exception {
		if (args.length == 0) {
			System.out.println("specify one or more product_ids to determine shipping cost");
			return;
		}

		String productId = args[0];
		Channel channel = AMQPCommon.connect();
		byte[] message = productId.getBytes();
		String routingKey = "shipping.request.q";
		channel.basicPublish("", routingKey, null, message);
		AMQPCommon.close(channel);
	}
}
