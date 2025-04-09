### Introduction
This side project only implements basic chat room function.
Expect additional function can base on adding plugin to implement 
and keep chat room logic complexity at a low level.


### Server start
First create docker network with command `docker create network im_net`
then you can directly execute `docker compose up -d`.
All container will start soon, first time will take more time to download all dependency.

### Develop debug  
Can simply start with **docker-compose.env.yml** and **intellij** run button

### Project architecture
The project adopts a layered architecture, which includes
API layer (controller)
Object layer (component)
Behavior layer (service)
Repository
SQL layer (mapper)
The current project does not use cross-layer calls.
In addition, the data layer and the SQL layer are now on the same layer.
The project has separated websocket part into core module.
The contents of the core module are expected expected to be replaceable with **SocketConnection** interface.

### Responsibilities of each layer
1. **API layer**: the API entry point of front-end calls
2. **Object layer**: mainly serves as the decoupling function of services and is also the place where business logic is implemented.
3. **Behavior layer**: The logical implementation location for processing data,
and mainly processes data of the same type (such as tables with the same prefix) together
4. **Data layer**: Perform data operations and data can be cached data or SQL data even No-SQL data.
Avoid making a single SQL statement too complex and logical joins can be used at this layer.
5. **SQL layer**: SQL syntax is concentrated in this layer.