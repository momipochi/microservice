import { useLocation } from '@tanstack/react-router'
import { clsx, type ClassValue } from 'clsx'
import { twMerge } from 'tailwind-merge'

export function cn(...inputs: ClassValue[]) {
  return twMerge(clsx(inputs))
}

export const useQueryParams = () => {
  const { search } = useLocation()
  return new URLSearchParams(search)
}
