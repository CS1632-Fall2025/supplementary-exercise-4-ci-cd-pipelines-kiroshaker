package edu.pitt.cs;

import org.mockito.Mockito;

public interface Cat {
	public static Cat createInstance(InstanceType type, int id, String name) {
		switch (type) {
			case IMPL:
				return new CatImpl(id, name);
			case BUGGY:
				return new CatBuggy(id, name);
			case SOLUTION:
				return new CatSolution(id, name);
			case MOCK:
			    Cat cat = Mockito.mock(Cat.class);

    			// Backing variables for mock state
    			final boolean[] rented = { false }; 
    			final String[] catName = { name };

			    Mockito.when(cat.getId()).thenReturn(id);
    			Mockito.when(cat.getName()).thenAnswer(inv -> catName[0]);
				Mockito.when(cat.getRented()).thenAnswer(inv -> rented[0]);
    			Mockito.when(cat.toString()).thenAnswer(inv -> "ID " + id + ". " + catName[0]);
    			Mockito.doAnswer(inv -> { rented[0] = true; return null; }).when(cat).rentCat();
   				Mockito.doAnswer(inv -> { rented[0] = false; return null; }).when(cat).returnCat();
    			Mockito.doAnswer(inv -> { catName[0] = inv.getArgument(0); return null; }).when(cat).renameCat(Mockito.anyString());

    			return cat;
			default:
				assert(false);
				return null;
		}
	}

	// WARNING: You are not allowed to change any part of the interface.
	// That means you cannot add any method nor modify any of these methods.
	
	public void rentCat();

	public void returnCat();

	public void renameCat(String name);

	public String getName();

	public int getId();

	public boolean getRented();

	public String toString();
}
