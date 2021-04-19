
Link to Trello board:
https://trello.com/b/pdC2sZtN/photo-contest

Link to Api Documentation
http://localhost:8080/swagger-ui.html#/


BUILD AND RUN PROJECT STEPS
---
- 1 - Clone the repository.
- 2 - Create the Database with the existing SQL file (createDatabase.sql).
- 3 - Fill the Database with the existing SQL file (InsertInDatabase.sql).
- 4 - Build and run iPhoto project.
- 5 - Open the following link: http://localhost:8080/swagger-ui.html#/ or use another program to test the application.
- 6 - ENJOY!
---


![alt](https://i.imgur.com/hT7wUmn.gif)

![alt](https://i.imgur.com/uBJwD6M.gif)

![alt](https://i.imgur.com/jo190dO.gif)

![alt](https://i.imgur.com/bqrCrWl.gif)


---
                                                                 CATEGORIES
---

**NO AUTHORIZATION NEEDED**

**Get**
- Route - /api/categories

      All categories method

---

**ORGANIZER OPERATION**

**Get**

- Route -  /api/categories/categoryID

      Category by ID


---
          
---

**ORGANIZER OPERATION**

**Post**

- Route -  /api/categories

      Create a new category

---
                                                                 IMAGES
---

**AUTHORIZATION NEEDED**

**Get**
- Route - /api/images

      All images method

---

---

**AUTHORIZATION NEEDED**

**Post**

- Route -  /api/images

      Uploads a new image

---
                                                                 USERS
---

**Organizer OPERATION**

**Get**
- Route - /api/users

      Returns all users

---

**AUTHORIZATION NEEDED**

**Get**

- Route -  /api/users/userID

      Returns user by ID

---

---

**AUTHORIZATION NEEDED**

**Get**

- Route -  /api/users/leaderboard

      Returns the current leaderboard in iPhoto

---

---

**AUTHORIZATION NEEDED**

**Get**

- Route -  /api/users/userID/profile

      Returns the profile of the user

---

---

**AUTHORIZATION NEEDED**

**Get**

- Route -  /api/users/userID

      Returns the user

---

---

**AUTHORIZATION NEEDED**

**Get**

- Route -  /api/users/userID/notifications

      Returns the notifications of the user

---

---

**AUTHORIZATION NEEDED**

**Get**

- Route -  /api/users/userID/contests

      Returns the contests in which the user participates in

---

---

**AUTHORIZATION NEEDED**

**Post**

- Route -  /api/users

      Creates a new user

---

---

**AUTHORIZATION NEEDED**

**Put**

- Route -  /api/users/userID

      Updates user

---
