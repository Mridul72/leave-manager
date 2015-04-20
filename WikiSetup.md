Back to HomePage

---


I've found the Eclipse Wiki Editor Plugin here : http://eclipsewiki.sourceforge.net/

Setup of this wiki is relatively simple.

I've download it from http://sourceforge.net/projects/eclipsewiki/ extracted it into eclipse/plugins/ directory and restarted Eclipse.

To know how it works I've looked in Help/Help Contents/Eclipse Wiki.

To have the wiki shortcuts (like F3 to create/follow a linked page), I've check "EclipseWiki" from "Command Group Availability" into the Window/Customize perspective menu

**Just something to which we must be careful** : when exporting wiki to HTML, I wanted to put the exported files into my Eclipse project and for this I should not chose /myProject/myWiki as Export Folder but the absolute path to the folder from the root of my hard drive (the error message returned was not very clear and it took me a while to understand the problem).

**After using it a while it appears that this wiki does not keep its promises.** The direct inclusion of codes between the project and the wiki that were promised in the documentation do not work ... indentation is lost, the code is unreadable. The uploading of the wiki is impractical (conversion to HTML and then manual upload).
This wiki is only usefull for a whole team using Eclipse. But in my case, the wiki must be visible/updated by my supervisor(s) without having to install Eclipse...