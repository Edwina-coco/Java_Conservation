package Pojo;

import java.util.ArrayList;

public class Store_User {
	ArrayList<User> arrayList;
	public Store_User() {
		// TODO Auto-generated constructor stub
	
	arrayList=new ArrayList<User>();
	}
	
	
	public void delete_User(User user)
	{
		arrayList.remove(user);
	}
	
	public void add_User(User user)
	{
		arrayList.add(user);
	}
	
	public int getnumbers()
	{
		return arrayList.size();
	}
	
	public User get_UserByName(String name)
	{
		for (User user : arrayList) {
			if(user.getUsername().equals(name))
				return user;
		}
		return null;
	}
	
	public User get_UserByIndex(int index)
	{
		if(getnumbers()==0)return null;
		return arrayList.get(index);
	}
}
