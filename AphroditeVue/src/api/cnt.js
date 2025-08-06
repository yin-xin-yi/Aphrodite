import { GetUserId } from "@/utils/auth";
import service from "@/utils/service";

const API_PATH = {
  LIKE_PATH: "/data/like",
  REGISTER_PATH: "/auth/register",
};

export const ClickLike = async (postid) => {
  let userId = GetUserId();
  console.log("clicklike", userId);
  return await service
    .post(API_PATH.LIKE_PATH, {
      userId: userId,
      postId: postid,
    })
    .then((response) => {
      console.log("ClickLike response:", response); 
      return response;
    })
    .catch((error) => {
      throw error;
    });
};
