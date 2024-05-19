package pl.edu.agh.gem.helper.group

import pl.edu.agh.gem.dto.GroupMemberResponse
import pl.edu.agh.gem.dto.GroupMembersResponse
import pl.edu.agh.gem.helper.group.DummyGroup.OTHER_GROUP_ID
import pl.edu.agh.gem.helper.user.DummyUser.USER_ID
import pl.edu.agh.gem.model.GroupMember
import pl.edu.agh.gem.model.GroupMembers

object DummyGroup {
    const val GROUP_ID = "groupId"
    const val OTHER_GROUP_ID = "otherGroupId"
}

fun createGroupMembersResponse(
    user: String = USER_ID,
) = GroupMembersResponse(
    members = listOf(
        createGroupMemberResponse(
            id = user,
        ),
    ),
)

fun createGroupMembersResponse(
    users: List<String> = listOf(USER_ID, OTHER_GROUP_ID),
) = GroupMembersResponse(
        members = users.map { createGroupMemberResponse(it) },
)

fun createGroupMemberResponse(
    id: String = USER_ID,
) = GroupMemberResponse(
    id = id,
)

fun createGroupMembers(
    user: String = USER_ID,
) = GroupMembers(
    members = listOf(
        createGroupMember(
            id = user,
        ),
    ),
)

fun createGroupMembers(
    users: List<String> = listOf(USER_ID, OTHER_GROUP_ID),
) = GroupMembers(
        members = users.map { createGroupMember(it) },
)

fun createGroupMember(
    id: String = USER_ID,
) = GroupMember(
    id = id,
)
