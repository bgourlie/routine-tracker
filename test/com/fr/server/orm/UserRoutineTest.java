package com.fr.server.orm;

import junit.framework.TestCase;

import org.hibernate.Session;

import com.fr.client.data.types.RoutineSession;
import com.fr.client.data.types.UserRoutine;

public class UserRoutineTest extends TestCase {
	private Session session;

	protected void setUp() {
		session = HibernateUtil.getSessionFactory().getCurrentSession();

	}

	protected void tearDown() {
		HibernateUtil.getSessionFactory().close();
	}

	public void testUserRoutineSaveDelete() {
		session.beginTransaction();
		UserRoutine userRoutine = new UserRoutine();
		userRoutine.setRoutineName("hibernate-test");
		userRoutine.setLocked(false);
		userRoutine.setUserID(3);
		session.save(userRoutine);
		session.getTransaction().commit();
		session.delete(userRoutine);
		session.getTransaction().commit();
	}

	public void testRoutineSessionSaveDelete() {
		session.beginTransaction();
		RoutineSession routineSession = new RoutineSession();
		routineSession.setUserRoutineID(1);
		routineSession.setRoutineSessionName("hibernate-test");
		routineSession.setOrderIndex(0);
		session.save(routineSession);
		session.getTransaction().commit();
		session.delete(routineSession);
		session.getTransaction().commit();
	}
}
