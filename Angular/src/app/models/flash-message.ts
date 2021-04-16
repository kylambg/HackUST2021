export interface FlashMessage{
    message: string
    title?: string
    type: 'alert'|'success'|'info'|'danger'
    time: number 
}