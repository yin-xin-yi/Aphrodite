
export function TimeFormat(str){
    if(!str) return "";
    
    const date = new Date(str);
    const now = new Date();
    const dif = Math.floor(now - date)/1000;

    if(dif < 60)
    {
        return '刚刚';
    }
    else if(dis<3600)
    {
        return `${Math.floor(dif/60)}分钟前`;
    }
    else if(dis<86400){
        return `${Math.floor(dif/3600)}小时前`;
    }

    return date.toLocaleDateString();
}