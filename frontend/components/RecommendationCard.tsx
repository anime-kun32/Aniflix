import {infoScreenState} from "@atoms/InfoScreenAtom";
import {Status} from "@enum/Status";
import AddToLibraryIcon from "@icons/AddToLibraryIcon";
import HoveringPlayIcon from "@icons/HoveringPlayIcon";
import {Recommendation} from "@interfaces/Recommendation";
import Image from "next/image";
import router from "next/router";
import React from "react";
import {useSetRecoilState} from "recoil";

interface Props {
    recommendation: Recommendation
}

export default function RecommendationCard({recommendation}: Props) {
    const title: string = recommendation.title.romaji ? recommendation.title.romaji : recommendation.title.english;
    
    const setShowInfoScreen = useSetRecoilState(infoScreenState)
    const handleClickedRecommendation = async () => {
        setShowInfoScreen(false);
        await router.push('/watch/[anime_id]/[episode_id]', `/watch/${recommendation?.id}/1`)
    }
    
    return (
        <div className={"m-[0.1em] min-h-[18em] h-[100%] cursor-pointer rounded bg-[#2f2f2f] overflow-hidden"}>
            <div className={"relative overflow-hidden"} onClick={() => handleClickedRecommendation()}>
                <Image
                    src={recommendation.image}
                    alt={title}
                    className={"aspect-video object-cover brightness-75 z-10"}
                    width={2500}
                    height={2500}
                />
                <HoveringPlayIcon/>
                <span
                    className={"text-white font-poppins font-light absolute top-[5%] right-[5%]"}>{recommendation.episodes} Episodes</span>
            </div>
            <div className={"min-h-[100%] px-[1em]"}>
                <div className={"flex flex-row justify-between py-[1em]"}>
                    <div>
                        <p className="text-[#46d369]  font-poppins">
                            {recommendation?.rating ? recommendation.rating : "0"}% match
                        </p>
                        <p className="text-white font-light font-poppins">
                            {recommendation?.status ? recommendation.status : Status.UNKNOWN}
                        </p>
                    </div>
                    <AddToLibraryIcon/>
                </div>
                <p className={"text-[#d2d2d2] font-poppins font-light text-sm"}>
                    {title}
                </p>
            </div>
        </div>
    )
}


