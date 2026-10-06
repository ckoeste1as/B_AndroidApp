$ErrorActionPreference = 'Stop'
$projectRoot = Split-Path -Parent $PSScriptRoot
$wrapperDirectory = Join-Path $projectRoot 'gradle/wrapper'
$wrapperJar = Join-Path $wrapperDirectory 'gradle-wrapper.jar'
$download = Join-Path $wrapperDirectory 'gradle-wrapper.jar.download'

if (Test-Path -LiteralPath $wrapperJar) { exit 0 }

try {
    Write-Host 'Lade den offiziellen Gradle 8.13 Wrapper ...'
    Invoke-WebRequest -Uri 'https://raw.githubusercontent.com/gradle/gradle/v8.13.0/gradle/wrapper/gradle-wrapper.jar' -OutFile $download
    $checksumResponse = Invoke-WebRequest -Uri 'https://services.gradle.org/distributions/gradle-8.13-wrapper.jar.sha256' -UseBasicParsing
    $checksumText = if ($checksumResponse.Content -is [byte[]]) {
        [System.Text.Encoding]::UTF8.GetString($checksumResponse.Content)
    } else { [string]$checksumResponse.Content }
    $checksum = $checksumText.Trim().ToLowerInvariant()
    if ($checksum -notmatch '^[a-f0-9]{64}$') { throw 'Ungueltige Wrapper-Pruefsumme.' }
    $actual = (Get-FileHash -LiteralPath $download -Algorithm SHA256).Hash.ToLowerInvariant()
    if ($actual -ne $checksum) { throw 'Die Wrapper-Pruefsumme stimmt nicht ueberein.' }
    Move-Item -LiteralPath $download -Destination $wrapperJar
    Write-Host 'Gradle Wrapper installiert und SHA-256 geprueft.'
} catch {
    Write-Error "Wrapper konnte nicht installiert werden: $_"
    exit 1
} finally {
    if (Test-Path -LiteralPath $download) { Remove-Item -LiteralPath $download }
}
