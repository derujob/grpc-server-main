package com.imertyildiz.grpcserver;

import com.imertyildiz.grpcproto.HelloWorldRequest;
import com.imertyildiz.grpcproto.HelloWorldResponse;
import com.imertyildiz.grpcproto.HelloWorldServiceGrpc;
import com.imertyildiz.grpcserver.Service.GreeterServer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.ManagedChannel;
import io.grpc.testing.GrpcCleanupRule;
import org.junit.Rule;
import static org.junit.Assert.assertEquals;

@SpringBootTest
class GrpcServerApplicationTests {

	@Rule
	public final GrpcCleanupRule grpcCleanup = new GrpcCleanupRule();

	@Test
	public void testSendMessageFromFederateService() throws Exception {
		// Create a unique server name for the in-process server
		String serverName = InProcessServerBuilder.generateName();

		// Register and start the in-process server
		grpcCleanup.register(
				InProcessServerBuilder
						.forName(serverName)
						.directExecutor()
						.addService(new GreeterServer())
						.build()
						.start()
		);

		// Create a channel and a client stub
		ManagedChannel channel = grpcCleanup.register(
				InProcessChannelBuilder
						.forName(serverName)
						.directExecutor()
						.build()
		);

		HelloWorldServiceGrpc.HelloWorldServiceBlockingStub stub = HelloWorldServiceGrpc.newBlockingStub(channel);

		// Make a request
		HelloWorldRequest request = HelloWorldRequest.newBuilder()
				.setRequestMessage("Hello")
				.setClientName("Federate Service")
				.build();

		// Stub a response
		HelloWorldResponse response = stub.helloWorld(request);

		// Verify the response
		assertEquals("Hello "+request.getClientName()+" !!!", response.getResponseMessage());
	}
}