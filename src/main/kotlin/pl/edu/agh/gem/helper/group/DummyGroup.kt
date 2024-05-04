package pl.edu.agh.gem.helper.group

import pl.edu.agh.gem.dto.GroupMemberResponse
import pl.edu.agh.gem.dto.GroupMembersResponse
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
                        )
        )
)

fun createGroupMember(
    id: String = USER_ID,
) = GroupMember(
        id = id,
)
