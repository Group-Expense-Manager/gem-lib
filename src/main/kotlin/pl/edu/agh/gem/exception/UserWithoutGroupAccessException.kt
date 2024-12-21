package pl.edu.agh.gem.exception

class UserWithoutGroupAccessException(
    userId: String,
) : RuntimeException("User with id:$userId is not a member of the group")
