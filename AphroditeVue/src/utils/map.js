
import { TimeFormat } from "./timeformat";
export function MapPostToCardData (postdata){
  let finalUsername = "匿名用户";
  let finalAvatar = "https://api.multiavatar.com/匿名.svg";
  let finalUserId = null;

  if (!postdata.isAnonymous && postdata.user) {
    finalUsername = postdata.user.username;
    finalAvatar = `https://api.multiavatar.com/${postdata.user.username}.svg`;
    finalUserId = postdata.user.userId;
  }

  return {
    author: {
      username: finalUsername,
      userid: finalUserId,
      avatar: finalAvatar,
    },
    post: {
      id: postdata.post.postId,
      content: postdata.post.content,
      title: postdata.post.title || "",
    },
    time: {
      create_at: TimeFormat(postdata.time.createdAt),
      update_at: TimeFormat(postdata.time.updatedAt),
    },
    count: {
      views: postdata.count.viewCount,
      likes: postdata.count.likeCount,
      comments: postdata.count.commentCount,
    },
  };
};