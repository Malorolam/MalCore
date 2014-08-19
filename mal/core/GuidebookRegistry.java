package mal.core;

import java.util.ArrayList;

import mal.core.util.MalLogger;

/**
 * This will store all active guidebook pages in the order they are registered
 * @author Mal
 *
 */
public class GuidebookRegistry {

	public static GuidebookRegistry instance = new GuidebookRegistry();
	
	private ArrayList<GuidebookPage> pageList = new ArrayList<GuidebookPage>();
	
	public GuidebookRegistry()
	{
		
	}
	
	public void addPage(GuidebookPage page)
	{
		MalLogger.addLogMessage("Added page with title: " + page.getPageTitle());
		pageList.add(page);
	}
	
	public GuidebookPage getPage(int index)
	{
		if(index<pageList.size())
			return pageList.get(index);
		return null;
	}
	
	public int getPageCount()
	{
		return pageList.size();
	}
}
