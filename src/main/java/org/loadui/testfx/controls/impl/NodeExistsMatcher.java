package org.loadui.testfx.controls.impl;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.junit.internal.matchers.TypeSafeMatcher;
import org.loadui.testfx.GuiTest;

public class NodeExistsMatcher extends TypeSafeMatcher<String>
{

	@Override
	public void describeTo( Description desc )
	{
		desc.appendText( "should exist" );
	}

	@Factory
	public static Matcher<String> exists()
	{
		return new NodeExistsMatcher();
	}

	@Override
	public boolean matchesSafely( String query )
	{
		return GuiTest.exists( query );
	}
}
