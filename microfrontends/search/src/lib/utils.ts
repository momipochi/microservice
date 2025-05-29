import { clsx } from 'clsx'
import { twMerge } from 'tailwind-merge'
import type { ClassValue } from 'clsx'
import { useLocation } from '@tanstack/react-router'

export function cn(...inputs: Array<ClassValue>) {
  return twMerge(clsx(inputs))
}

export const useQueryParams = () => {
  const { search } = useLocation()
  return new URLSearchParams(search)
}
