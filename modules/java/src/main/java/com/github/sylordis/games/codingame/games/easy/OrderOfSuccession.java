package com.github.sylordis.games.codingame.games.easy;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class OrderOfSuccession {

	enum Gender {
		F, M;
	}

	enum Religion {
		ANGLICAN, CATHOLIC;
	}

	static final class Utils {

		public static final int YEAR = Calendar.getInstance().get(Calendar.YEAR);
	}

	class IsEligibleToSuccession implements Predicate<Person> {

		@Override
		public boolean test(Person t) {
			return t.getReligion() != Religion.CATHOLIC && t.isAlive();
		}

	}

	static class PersonPdu {
		private final Person person;
		private final String parent;

		public PersonPdu(Person person, String parent) {
			this.person = person;
			this.parent = parent;
		}

		/**
		 * @return the person
		 */
		public Person getPerson() {
			return person;
		}

		/**
		 * @return the parent
		 */
		public String getParent() {
			return parent;
		}
	}

	static class Person implements Comparable<Person> {

		private final String name;
		private final int birth;
		private final int death;
		private final Religion religion;
		private final Gender gender;
		private Person parent;
		private final List<Person> children;

		public Person(String name, int birth, Religion religion, Gender gender) {
			this(name, birth, -1, religion, gender);
		}

		public Person(String name, int birth, int death, Religion religion, Gender gender) {
			this.name = name;
			this.birth = birth;
			this.death = death;
			this.religion = religion;
			this.gender = gender;
			this.children = new ArrayList<>();
		}

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @return the birth
		 */
		public int getBirth() {
			return birth;
		}

		/**
		 * @return the death
		 */
		public int getDeath() {
			return death;
		}

		public boolean isAlive() {
			return death == -1;
		}

		/**
		 * @return the religion
		 */
		public Religion getReligion() {
			return religion;
		}

		/**
		 * @return the gender
		 */
		public Gender getGender() {
			return gender;
		}

		/**
		 * @return the parent
		 */
		public Person getParent() {
			return parent;
		}

		/**
		 * @param parent
		 *            the parent to set
		 */
		public void setParent(Person parent) {
			this.parent = parent;
		}

		/**
		 * @return the children
		 */
		public List<Person> getChildren() {
			return children;
		}

		public void addChild(Person person) {
			children.add(person);
		}

		public boolean isDead() {
			return Utils.YEAR <= death;
		}

		public int getGeneration() {
			Person node = this;
			int count = 1;
			while (node.hasParent()) {
				count++;
				node = node.getParent();
			}
			return count;
		}

		public boolean hasParent() {
			return parent != null;
		}

		@Override
		public int compareTo(Person o) {
			int index = getGeneration() - o.getGeneration();
			// Gender
			if (index == 0)
				index = o.getGender().ordinal() - gender.ordinal();
			// Age
			if (index == 0)
				index = birth - o.getBirth();
			return index;
		}

		@Override
		public boolean equals(Object o) {
			return o != null && o instanceof Person && name.equals(((Person) o).getName());
		}

		public List<Person> getFamily() {
			List<Person> family = new ArrayList<>();
			family.add(this);
			children.forEach(c -> family.addAll(c.getFamily()));
			return family;
		}

		@Override
		public String toString() {
			return name + children;
		}

	}

	private List<Person> people;

	public void setFamily(List<Person> family) {
		this.people = family;
	}

	public static PersonPdu createPersonPduFrom(String line) {
		String parent = null;
		String name = null;
		int birth = Utils.YEAR;
		int death = Utils.YEAR;
		Religion religion = null;
		Gender gender = null;
		String[] parts = line.split(" ");
		name = parts[0];
		parent = parts[1].equals("-") ? null : parts[1];
		birth = Integer.parseInt(parts[2]);
		death = parts[3].equals("-") ? -1 : Integer.parseInt(parts[3]);
		religion = Religion.valueOf(parts[4].toUpperCase());
		gender = Gender.valueOf(parts[5].toUpperCase());
		Person person = new Person(name, birth, death, religion, gender);
		return new PersonPdu(person, parent);
	}

	public List<Person> getSuccessionPlan() {
		List<Person> plan = new ArrayList<>();
		// Sort children according to succession
		people.forEach(p -> Collections.sort(p.getChildren()));
		// FlatMap
		Person root = people.stream().filter(p -> !p.hasParent()).findFirst().get();
		plan = root.getFamily();
		// Filter non eligible
		plan = plan.stream().filter(new IsEligibleToSuccession()).collect(Collectors.toList());
		return plan;
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int npersons = in.nextInt();
		in.nextLine();
		Map<String, PersonPdu> brokenFamily = new HashMap<>();
		for (int n = 0; n < npersons; n++) {
			PersonPdu pdu = createPersonPduFrom(in.nextLine());
			brokenFamily.put(pdu.getPerson().getName(), pdu);
		}
		in.close();
		// Reestablish family links
		for (PersonPdu pdu : brokenFamily.values()) {
			PersonPdu parentPdu = brokenFamily.get(pdu.getParent());
			if (parentPdu != null) {
				pdu.getPerson().setParent(parentPdu.getPerson());
				parentPdu.getPerson().addChild(pdu.getPerson());
			}
		}
		OrderOfSuccession oos = new OrderOfSuccession();
		oos.setFamily(brokenFamily.values().stream().map(pdu -> pdu.getPerson()).collect(Collectors.toList()));
		oos.getSuccessionPlan().forEach(p -> System.out.println(p.getName()));
	}

}
