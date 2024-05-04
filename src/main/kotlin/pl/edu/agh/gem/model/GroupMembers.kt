package pl.edu.agh.gem.model

data class GroupMembers(
    val members: List<GroupMember>,
)

data class GroupMember(
    val id: String,
)
