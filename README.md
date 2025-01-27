   # Dungeon Crawler 

### Description 
One of the first text-based videogames was Zork. This game is based of the principles of Zork, which is considered a
Dungeon Crawler. What I did different in my project was that instead of having a hardcoded game that is always the same,
I opted to create a framework which reads the whole game from a .zork file. As a guide I used a [University Course](http://stephendavies.org/cpsc240/?page_id=59) from the 
University of Mary Washington, to help me create my structure and get all basics down. Additionaly I created a new Window
where the game takes place.
<br>
<br>
This is the first version of the Dungeon Crawler. 
I did not make this class diagram. 
[image](https://github.com/user-attachments/assets/4bfa3cb2-c365-42fa-8eb4-f99fe5709f1f)

[link to original](http://stephendavies.org/cpsc240/zorkI.html)
<br>
<br>
## Class Diagram

![Class_Diagram_M320 drawio](https://github.com/user-attachments/assets/f8ed9f01-1fab-4fbf-b292-d0924c1b8c50)
<br>
<br>
## Sequence Diagram 

This diagram shows how the application reacts when a move command is used in the input. 

![Sequenz_Diagram_M320 drawio](https://github.com/user-attachments/assets/7c2b1847-acd6-4c0d-b2fb-3f9714a5ee96)

### Design Patterns 
I implemented the Singleton Design Pattern in my Project for my command structure, to ensure that there is only a instance
of the CommandFactory. It also makes it simple to use the Commands anywhere in the Code since it already is initialized in itself.
The main reason for using it, is simply that there isn't any redundant initialization of the Commands. 

