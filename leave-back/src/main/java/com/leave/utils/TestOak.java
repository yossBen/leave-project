package com.leave.utils;

import java.net.UnknownHostException;

import javax.jcr.LoginException;
import javax.jcr.Node;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;

import org.apache.jackrabbit.oak.Oak;
import org.apache.jackrabbit.oak.jcr.Jcr;
import org.apache.jackrabbit.oak.plugins.document.DocumentNodeStore;
import org.apache.jackrabbit.oak.plugins.document.mongo.MongoDocumentNodeStoreBuilder;

public class TestOak {
	public static void main(String[] args) throws UnknownHostException, LoginException, RepositoryException {
		String uri = "mongodb://localhost:27017";
		DocumentNodeStore ns = new MongoDocumentNodeStoreBuilder().setMongoDB(uri, "oak-last-version", 16).build();
		System.out.println("connexion");

		Repository repo = new Jcr(new Oak(ns)).createRepository();

		System.out.println("Creation REPO");

		Session session = repo.login(new SimpleCredentials("admin", "admin".toCharArray()));
		Node parentNode = session.getRootNode();
//		Node node = session.getNode(parentNode.getPath() + "test");
		// Node parentNode = session.getRootNode();
		Node childNode = parentNode.addNode("test2");
		childNode.setProperty("ville", "Argenteuil");

		session.save();
		System.out.println("FIN");
	}
}