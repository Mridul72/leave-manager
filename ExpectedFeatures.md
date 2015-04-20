# Expected Features #

## “site map” ##

  * **First page**
    * login page : login / password form
  * **Menu** (visible after the identification)
    * **User account**
      * home page : Summary of earned, taken or to be taken leave
      * display : list or calendar
      * management of my account
      * new request for leave
    * **Admin section** (restricted access rights based)
      * display : Summary of users’ leave / calendar or list vue (restricted to manager at least)
      * users management (HR at least)
      * groups management (same schedule and same number of days by type of leave) (HR at least)
      * teams management (HR at least)
      * types of leave management (HR at least)
      * global settings (admin only)

---

## (user) home page ##

Summary of earned, taken or to be taken leave.

List of types of leave with the number of days worked, total number of days of earned leave, number of taken or to be taken   leave, etc.

## (user) : display ##

Toolbar which give access to calendar or list  to have a list or calendar overview + legend for the color code.

  * ### calendar ###

Name + a colored disc (color code for the status of the leave, accepted, rejected, pending ...).

Double click on a date : opening of a new leave request with date pre-filled.

Right click on a personal leave : menu update / delete (only for holiday dates not exceeded).

  * ### list ###

Toolbar to filter the list by date (beginning / end), by status.

By default, requests are displayed for the current day or the future, in chronological order

List of requested leave with each line in color :

type of leave, dates, officials (manager(s)/HR), statut (pending => adding a reference to managers who may have validated at their level, alternative => alternative dates proposed, validate, used, etc.), attachments, edit / delete


If the lines are too long, display of a small + beside to expand and view additional information : ground for refusal, alternative dates proposed, details of officials who have completed, etc.


(navigation <<   <   1 - 2 - 3   >   >>  if necessary...)

## (user) management of my account ##

update of my email / login / password / etc.

## (user) new request for leave ##

form :
  * date (the current one automatically)
  * emitter
  * only for HR/admin : validate the request at the same time.
  * person concerned
  * only for a manager/HR/admin : person to replace him in his office during his absence
  * leaders (those who validate)
  * from the DD/MM/YYYY (button to choose on a calendar) + am /pm
  * to the DD/MM/YYYY (button to choose on a calendar) + am / pm
  * [number of hours / days](summary.md)
  * type of leave (combo)
  * comment (text field)
  * document to be uploaded (file explorer knob)
  * [of attached files with cross to destroy](summary.md)

=> send an email to the manager for validation.

=> leave is specified to within about a half-day : date + am / pm / full day

## (admin, HR or manager) : display ##

Toolbar to filter the list by status (pending, validated, etc...), by employee, by team, by group.

By default, pending requests are displayed. (dates not exceeded).

  * ### global statistics ###

For an employee, view the different types of leave and their status (days taken, remaining, etc. ...).

  * ### calendar ###

Name + a colored disc (color code for the status of the leave, accepted, rejected, pending ...).

right click on the leave request : menu accept/reject/proposed alternative/delete (admin only)

Double click on a date: opening the types of leave management to add a particular constraint on the current date ...

  * ### list ###

Toolbar to filter the list by dates

list of requested leave with :
  * requesting, ...., attachments, status, Reason for refusal / comment,
  * allow / deny / suggest alternative / delete (admin only)

(navigation <<   <   1 - 2 - 3   >   >>)

The refusal is blocking : if a leave is rejected, even by a "optional" manager , the refusal is returned to the user.

acceptance of a "required" manager unlocks the passage to the next (the request appears in the admin section of the next manager).

If a manager is "optional" demand also appears in the admin section of the next "required" manager.

Only the admin can edit or delete a date confirmed or a past date

The HR can create a leave request for an employee (submit button included to create and validate at the same time).

HR can change a past and validated leave : change the type of the leave request (toggle off to RTT).

a manager / HR may impose a discharge date and confirm.

a user can edit / delete only non-past (and non-validated) leave that he himself has created. But he can add a justification on special leave.

cancellation of a leave approved => Should it go through the workflow?

Keep a history of proposals for alternative dates for a leave.

For the workflow => a manager sees a request only if the previous manager has validated the request (unless the manager is not reported as "blocking").

## (admin or HR) users management ##

create / edit / delete (admin only) a user account by the HR

=> the employee receives its codes of session by email (randomly generated code)

list of users with a button : Add a user

Title, name, email, group, team, manager (yes / no) / of the team(s), date of taking office (dd / mm / yyyy), date of termination (dd / mm / yyyy ), number of days allocated for each type of leave.

Clicking on a user => display details :
  * setting the workflow of the teams for a manager => checkboxes : validation of this manager required / optional for each team
  * button "add a holiday".

HR account: posibility of adding a day off for the employee.

## (admin or HR) groups management ##

create / modify / delete (admin only) a group by the HR

name, number of days allocated to each type of leave, working hours (display a "typical week" hours worked as a small graphic planning)

typical week : a list of seven days, check am / pm / both + value of the day in "standard time"

possibility of creating a group on the model of another for not having all to re-fill (the setting of the group model is duplicated)

## (admin or HR) teams management ##

create / modify / delete (admin only) a team by the HR

name, manager(s), expiration of the rights of a manager

settings for a team : specify workflow => checkboxes: validation required / optional for each manager

## (admin or HR) types of leave management ##

create / modify / delete (admin only) a type of leave by the HR

  * Type of leave (annual, RTT) :

name, a number of N hours(s) / Day(s) / week(s) / month(s) / year(s) of work entitles N "standard times" leave for the group(s) / all with a maximum of X hour(s) / Day(s) / week(s) / month / year(s) per year with a maximum of X hour(s) / Day(s) / week(s ) / month(s) / year(s) in a row.

  * Special leave (maternity, paternity, illness) :

name, with a maximum of X hour(s) / Day(s) / week(s) / month(s) / year(s) per year with a maximum of X hour(s) / Day(s) / week(s) / month(s) / year(s) in a row. (for group(s) / man / woman / all), evidence to provide yes / no

  * Constraint (fixed holiday, solidarity Day) :

name, forced to work / be idle from DD / MM / YYYY (to DD / MM / YYYY) (for group(s) / user (s) / all)

  * Time savings account :

enabled yes / no (with a maximum of N "standard times" per year)


=> Warning : there are days worked during holidays (stress) that do not qualify for leave. and non-work days that are eligible for leave. must be able to tell.

=> for quantities per year and maximum row : these attributes vary from one group to another. default in a "default group" + special setting for the group (optional)

For each employee the gain of leave is calculated according to the timetable that is defined in the group (Y time worked entitles Z "standard times" - 1 day = X "standard times" => the user is therefore entitled to N days off).

## (admin) Global settings ##

global unity in software for durations => "standard time"

  * Access rights
    * user : normal user sees only what concerns, limited access
    * manager : responsible for a team he may reject / validate the holidays, a manager may be responsible for several teams, in this case he validates leave for the manager below him. A manager can not validate its own holidays (unless no one is above him, ie if no HR is still defined).
    * RH : manager who has special rights to all teams / all users, including himself. Can validate their own leave
    * admin : owns all rights, including rights to destroy as special leave already passed, etc. …

Foresee the parameterization of rights at the super-admin account in the form of a grid with
  * in the left column: a typical user, different managers and different HR
  * in the first row: the different elements for which we must authorize or deny access.
  * in each case a "switch" to enable / disable the right.

Foresee the possibility that HR must validate after the manager => HR must not validate what is not seen by the manager.

When the manager goes on leave he must appoint a manager replacement. Foresee that a manager who has levels 1 and 2 must not validate two times.

Each user has access to the user section where he sees only the information concerning him. The "admin" is only visible to the manager, HR and admin only see information that they have the right to change.


---


## FUTURE UPDATES ? ##

schedules display a month at a time => provide display of a week or a year ...

change the display format of dates

work divided into two half day  => include night work ?