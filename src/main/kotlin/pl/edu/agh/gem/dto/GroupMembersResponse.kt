package pl.edu.agh.gem.dto

import pl.edu.agh.gem.model.GroupMember
import pl.edu.agh.gem.model.GroupMembers

data class GroupMembersResponse(
    val members: List<GroupMemberResponse>,
)

data class GroupMemberResponse(
    val id: String,
)

fun GroupMembersResponse.toDomain() = GroupMembers(
    members = members.map { it.toDomain() },
)

fun GroupMemberResponse.toDomain() = GroupMember(
    id = id,
)
