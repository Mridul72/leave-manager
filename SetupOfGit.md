# Using Git with Eclipse and Google Code #

**To use Git with my Eclipse project** : I have added a Egit plugin. For this I have open Help/Install New Software, I have put http://download.eclipse.org/egit/updates in the "work with" field and choose Eclipse EGit and Eclipse JGit (although I'm not sure that JGit is useful) for install.

After installing it (it takes some time), I have checked Git into the `Window/Customize perspective/Command groups availability` menu to have Git menu and icons shortcuts.

I have then done a right-click on my project, choose Team/Share Project/Git and then specify the local directory where I want to keep my personal commits. **Warning !!**, if I choose anything else than "use or create repository in parent folder of project", my API is not in the tomcat/webapps folder anymore and I can't deploy it anymore...

To synchronize online, on my google code project page, I'm gone to Administer/Source, and then click the button to switch to Git, then in Source/Checkout to get the repository URL, my login (email adress) and password.

**To test** : Into Eclipse I first do a commit (to have something to push online), for this I right click on my project and choose Team/Commit... then right click again on my project and choose Team/Remote/Push. In the window displayed I indicated the repository URL, my login and password.

**To memorize repository** : I right click on my project and choose Team/Show in repositories view and then right click on "Remotes" and choose "Create Remote...", choose a name for the repository and specify the informations. Now When I do a "push" I can directly use the google code repository.

Just a problem, I must re-enter the password each time I want to do a push...