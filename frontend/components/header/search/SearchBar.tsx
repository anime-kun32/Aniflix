import {searchResultsState, showSearchResultsState} from "@atoms/SearchResultScreen";
import AnimeService from "@consumet/AnimeService";
import {MagnifyingGlassIcon} from "@heroicons/react/24/solid";
import React, {useEffect, useRef, useState} from "react";
import {useSetRecoilState} from "recoil";

const SearchBar = () => {
    const [isExpanded, setExpanded] = useState(false);
    const [searchTerm, setSearchTerm] = useState("");
    const inputElement = useRef<HTMLInputElement>(null);
    const setShowSearchResults = useSetRecoilState(showSearchResultsState);
    const setSearchResults = useSetRecoilState(searchResultsState);
    
    const handleKeyDown = (e: React.KeyboardEvent<HTMLInputElement>) => {
        console.debug(e.key)
        if (e.key === "Escape") {
            setSearchTerm("");
            setExpanded(false);
        }
    }
    
    const handleFocus = () => {
        if (!inputElement.current) return;
        setExpanded(!isExpanded)
        inputElement.current.focus();
    }
    
    useEffect(() => {
        if (isExpanded) {
            setSearchTerm("");
        }
    }, [isExpanded]);
    
    useEffect(() => {
        if (searchTerm.length > 0) {
            setShowSearchResults(true);
            AnimeService.searchAnime(searchTerm).then((res) => {
                setSearchResults(res);
            });
        } else {
            setSearchResults([]);
            setShowSearchResults(false);
        }
    }, [searchTerm]);
    
    return (
        <div className="relative flex flex-row items-center space-x-4">
            <input
                className={`${
                    isExpanded
                        ? "ease-out duration-500 w-48 mt-1 p-2 rounded border border-gray-400 transition-all"
                        : "w-0 ease-in duration-500"
                } bg-[#181818]/80 font-poppins text-sm`}
                onChange={(e) => setSearchTerm(e.target.value)}
                value={searchTerm}
                type="text"
                placeholder="Search"
                onKeyDown={(e) => handleKeyDown(e)}
                ref={inputElement}
            />
            <MagnifyingGlassIcon className="headerIcon" onClick={() => handleFocus()}/>
        </div>
    );
};

export default SearchBar;
