package com.fr.server.orm;

import org.hibernate.Session;

import com.fr.client.data.types.UserRoutine;

public class ORMTest {
	public static void main(String[] args) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		session.beginTransaction();

		/** test UserRoutine * */
		UserRoutine userRoutine = new UserRoutine();
		userRoutine.setRoutineName("hibernate-test");
		userRoutine.setUserID(3);

		session.save(userRoutine);

		session.getTransaction().commit();

		HibernateUtil.getSessionFactory().close();
	}

}
